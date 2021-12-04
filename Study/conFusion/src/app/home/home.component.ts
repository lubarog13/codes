import { Component, OnInit, Inject } from '@angular/core';
import { Dish } from '../shared/dish';
import { DishService } from '../services/dish.service';
import { Promotion } from '../shared/promotion';
import { PromotionService } from '../services/promotion.service';
import { Leader } from '../shared/leader';
import { LeaderService } from '../services/leader.service';
import { flyInOut, expand } from '../animations/app.animations';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
  // tslint:disable-next-line:use-host-property-decorator
  host: {
    '[@flyInOut]': 'true',
    'style': 'display: block;'
    },
    animations: [
      flyInOut(),
      expand()
    ]
})
export class HomeComponent implements OnInit {

  dish: Dish;
  promotion: Promotion;
  leader: Leader;
  errMess: string;
  errorMess: string;
  errorMessage: string;

  constructor(private dishservice: DishService,
    private promotionservice: PromotionService, private leaderservice: LeaderService, @Inject('BaseURL') public BaseURL) { }

  ngOnInit() {
    console.log(this.BaseURL)
    this.dishservice.getFeaturedDish().subscribe((dish) => this.dish = dish, errmess => this.errMess = <any>errmess );
    this.promotionservice.getFeaturedPromotion().subscribe((promotion)=> this.promotion = promotion, errmess => this.errorMess = <any>errmess);
    this.leaderservice.getFeaturedLeader().subscribe((leader) => this.leader = leader, errmess => this.errorMessage = <any> errmess);
    console.log(this.promotion)
  }

}
