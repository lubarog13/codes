/*
 *      Copyright 1973 Bell Telephone Laboratories Inc
 */


/*
 * variables
 */

#define NBUF    15
#define NINODE  100
#define NFILE   100
#define NMOUNT  5
#define NEXEC   4
#define MAXMEM  (32*32)
#define SSIZE   20
#define SINCR   20
#define NOFILE  15
#define CANBSIZ 256
#define CMAPSIZ 100
#define SMAPSIZ 100
#define NCALL   20
#define NPROC   50
#define NTEXT   20
#define NCLIST  100


/*
 * priorities
 * probably should not be
 * altered too much
 */

#define PSWP    -100
#define PINOD   -90
#define PRIBIO  -50
#define PPIPE   1
#define PWAIT   40
#define PSLEP   90
#define PUSER   100


/*
 * fundamental constants
 * cannot be changed
 */

#define USIZE   16
#define NULL    0
#define NODEV   (-1)
#define ROOTINO 1
#define DIRSIZ  14


struct proc {
        char    p_stat;
        char    p_flag;
        char    p_pri;
        char    p_sig;
        char    p_uid;
        char    p_time;
        int     p_ttyp;
        int     p_pid;
        int     p_ppid;
        int     p_addr;
        int     p_size;
        int     p_wchan;
        int     *p_textp;
} proc[NPROC];

/* stat codes */
#define SSLEEP  1
#define SWAIT   2
#define SRUN    3
#define SIDL    4
#define SZOMB   5

/* flag codes */
#define SLOAD   01
#define SSYS    02
#define SLOCK   04
#define SSWAP   010


struct user {
        int     u_rsav[2];              /* must be first */
        int     u_fsav[25];             /* must be second */
        char    u_segflg;
        char    u_error;
        char    u_uid;
        char    u_gid;
        char    u_ruid;
        char    u_rgid;
        int     u_procp;
        char    *u_base;
        char    *u_count;
        char    *u_offset[2];
        int     *u_cdir;
        char    u_dbuf[DIRSIZ];
        char    *u_dirp;
        struct  {
                int     u_ino;
                char    u_name[DIRSIZ];
        } u_dent;
        int     *u_pdir;
        int     u_uisa[8];
        int     u_uisd[8];
        int     u_ofile[NOFILE];
        int     u_arg[5];
        int     u_tsize;
        int     u_dsize;
        int     u_ssize;
        int     u_qsav[2];
        int     u_ssav[2];
        int     u_signal[NSIG];
        int     u_utime;
        int     u_stime;
        int     u_cutime[2];
        int     u_cstime[2];
        int     *u_ar0;
        int     u_prof[4];
        char    u_nice;
        char    u_dsleep;
} u;    /* u = 140000 */




int     mpid;
char    runin;


#define PS      0177776
struct  {
        int integ;
};

sleep(chan, pri)
{
        register *rp, s;

        u.u_dsleep = 0;
        s = PS->integ;
        rp = u.u_procp;
        if(pri >= 0) {
                if(issig())
                        goto psig;
                rp->p_wchan = chan;
                rp->p_stat = SWAIT;
                rp->p_pri = pri;
                spl0();
                if(runin != 0) {
                        runin = 0;
                        wakeup(&runin);
                }
                swtch();
                if(issig()) {
                psig:
                        aretu(u.u_qsav);
                        return;
                }
        } else {
                rp->p_wchan = chan;
                rp->p_stat = SSLEEP;
                rp->p_pri = pri;
                spl0();
                swtch();
        }
        PS->integ = s;
}

wakeup(chan)
{
        register struct proc *p;
        register n, c;

loop:
        c = chan;
        n = 0;
        for(p = &proc[0]; p < &proc[NPROC]; p++)
                if(p->p_wchan == c) {
                        if(runout!=0 && (p->p_flag&SLOAD)==0) {
                                runout = 0;
                                n++;
                        }
                        p->p_wchan = 0;
                        p->p_stat = SRUN;
                        runrun++;
                }
        if(n) {
                chan = &runout;
                goto loop;
        }
}

sched()
{
        struct proc *p1;
        register struct proc *rp;
        register a, n;

        /*
         * find user to swap in
         * of users ready, select one out longest
         */

        goto loop;

sloop:
        runin++;
        sleep(&runin, PSWP);

loop:
        spl6();
        n = -1;
        for(rp = &proc[0]; rp < &proc[NPROC]; rp++)
        if(rp->p_stat==SRUN && (rp->p_flag&SLOAD)==0 &&
            rp->p_time > n) {
                p1 = rp;
                n = rp->p_time;
        }
        if(n == -1) {
                runout++;
                sleep(&runout, PSWP);
                goto loop;
        }

        /*
         * see if there is core for that process
         */

        spl0();
        rp = p1;
        a = rp->p_size;
        if((rp=rp->p_textp) != NULL)
                if(rp->x_ccount == 0)
                        a =+ rp->x_size;
        if((a=malloc(coremap, a)) != NULL)
                goto found2;

        /*
         * none found,
         * look around for easy core
         */

        spl6();
        for(rp = &proc[0]; rp < &proc[NPROC]; rp++)
        if((rp->p_flag&(SSYS|SLOCK|SLOAD))==SLOAD &&
            rp->p_stat == SWAIT)
                goto found1;
        /*
         * no easy core,
         * if this process is deserving,
         * look around for
         * oldest process in core
         */

        if(n < 3)
                goto sloop;
        n = -1;
        for(rp = &proc[0]; rp < &proc[NPROC]; rp++)
        if((rp->p_flag&(SSYS|SLOCK|SLOAD))==SLOAD &&
           (rp->p_stat==SRUN || rp->p_stat==SSLEEP) &&
            rp->p_time > n) {
                p1 = rp;
                n = rp->p_time;
        }
        if(n < 2)
                goto sloop;
        rp = p1;

        /*
         * swap user out
         */

found1:
        spl0();
        rp->p_flag =& ~SLOAD;
        xswap(rp, 1, 0);
        goto loop;

        /*
         * swap user in
         */

found2:
        if((rp=p1->p_textp) != NULL) {
                if(rp->x_ccount == 0) {
                        if(swap(rp->x_daddr, a, rp->x_size, B_READ))
                                goto swaper;
                        rp->x_caddr = a;
                        a =+ rp->x_size;
                }
                rp->x_ccount++;
        }
        rp = p1;
        if(swap(rp->p_addr, a, rp->p_size, B_READ))
                goto swaper;
        mfree(swapmap, (rp->p_size+7)/8, rp->p_addr);
        rp->p_addr = a;
        rp->p_flag =| SLOAD;
        rp->p_time = 0;
        goto loop;

swaper:
        panic("swap error");
}

swtch()
{
        static int *p;
        register i, n;
        register struct proc *rp;

        if(p == NULL)
                p = &proc[0];
        savu(u.u_rsav);
        retu(proc[0].p_addr);

loop:
        rp = p;
        p = NULL;
        n = 127;
        for(i=0; i<NPROC; i++) {
                rp++;
                if(rp >= &proc[NPROC])
                        rp = &proc[0];
                if(rp->p_stat==SRUN && (rp->p_flag&SLOAD)!=0) {
                        if(rp->p_pri < n) {
                                p = rp;
                                n = rp->p_pri;
                        }
                }
        }
        if(p == NULL) {
                p = rp;
                idle();
                goto loop;
        }
        rp = p;
        retu(rp->p_addr);
        sureg();
        if(rp->p_flag&SSWAP) {
                rp->p_flag =& ~SSWAP;
                aretu(u.u_ssav);
        }
        return(1);
}

newproc()
{
        int a1, a2;
        struct proc *p, *up;
        register struct proc *rpp;
        register *rip, n;

        for(rpp = &proc[0]; rpp < &proc[NPROC]; rpp++)
                if(rpp->p_stat == NULL)
                        goto found;
        panic("no procs");

found:
        /*
         * make proc entry for new proc
         */

        p = rpp;
        rip = u.u_procp;
        up = rip;
        rpp->p_stat = SRUN;
        rpp->p_flag = SLOAD;
        rpp->p_uid = rip->p_uid;
        rpp->p_ttyp = rip->p_ttyp;
        rpp->p_textp = rip->p_textp;
        rpp->p_pid = ++mpid;
        rpp->p_ppid = rip->p_pid;
        rpp->p_time = 0;

        /*
         * make duplicate entries
         * where needed
         */

        for(rip = &u.u_ofile[0]; rip < &u.u_ofile[NOFILE];)
                if((rpp = *rip++) != NULL)
                        rpp->f_count++;
        if((rpp=up->p_textp) != NULL) {
                rpp->x_count++;
                rpp->x_ccount++;
        }
        u.u_cdir->i_count++;

        /*
         * swap out old process
         * to make image of new proc
         */
        savu(u.u_rsav);
        rpp = p;
        u.u_procp = rpp;
        rip = up;
        n = rip->p_size;
        a1 = rip->p_addr;
        rpp->p_size = n;
        a2 = malloc(coremap, n);
        if(a2 == NULL) {
                rip->p_stat = SIDL;
                rpp->p_addr = a1;
                savu(u.u_ssav);
                xswap(rpp, 0, 0);
                rpp->p_flag =| SSWAP;
                rip->p_stat = SRUN;
        } else {
                rpp->p_addr = a2;
                while(n--)
                        copyseg(a1++, a2++);
        }
        u.u_procp = rip;
        return(0);
}

expand(newsize)
{
        int i, n;
        register *p, a1, a2;

        p = u.u_procp;
        n = p->p_size;
        p->p_size = newsize;
        a1 = p->p_addr;
        if(n >= newsize) {
                mfree(coremap, n-newsize, a1+newsize);
                return;
        }
        savu(u.u_rsav);
        a2 = malloc(coremap, newsize);
        if(a2 == NULL) {
                savu(u.u_ssav);
                xswap(p, 1, n);
                p->p_flag =| SSWAP;
                swtch();
                /* no return */
        }
        p->p_addr = a2;
        for(i=0; i<n; i++)
                copyseg(a1+i, a2++);
        mfree(coremap, n, a1);
        retu(p->p_addr);
        sureg();
}

main()
{
        extern schar;
        register i1, *p;

        /*
         * zero and free all of core
         */

        updlock = 0;
        UISA->r[0] = *ka6 + USIZE;
        UISD->r[0] = 077406;
        for(; fubyte(0) >= 0; UISA->r[0]++) {
                clearseg(UISA->r[0]);
                maxmem++;
                mfree(coremap, 1, UISA->r[0]);
        }
        printf("mem = %l\n", maxmem*10/32);
        maxmem = min(maxmem, MAXMEM);
        mfree(swapmap, nswap, swplo);

        /*
         * determine clock
         */

        UISA->r[7] = ka6[1]; /* io segment */
        UISD->r[7] = 077406;
        for(p=lksp;; p++) {
                if(*p == 0)
                        panic("no clock");
                if(fuword(*p) != -1) {
                        lks = *p;
                        break;
                }
        }

        /*
         * set up system process
         */
	mpid = 0;

        proc[0].p_addr = *ka6;
        proc[0].p_size = USIZE;
        proc[0].p_stat = SRUN;
        proc[0].p_flag =| SLOAD|SSYS;
        u.u_procp = &proc[0];

        /*
         * set up 'known' i-nodes
         */

        sureg();
        *lks = 0115;
        cinit();
        binit();
        iinit();
        rootdir = iget(rootdev, ROOTINO);
        rootdir->i_flag =& ~ILOCK;
        u.u_cdir = iget(rootdev, ROOTINO);
        u.u_cdir->i_flag =& ~ILOCK;


        /*
         * make init process
         * enter scheduling loop
         * with system process
         */

        if(newproc()) {
                expand(USIZE+1);
                u.u_uisa[0] = USIZE;
                u.u_uisd[0] = 6;
                sureg();
                copyout(icode, 0, 30);
                return;
        }
        sched();
}

