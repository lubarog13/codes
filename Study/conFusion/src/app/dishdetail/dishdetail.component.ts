import { Component, OnInit, ViewChild } from '@angular/core';
import { Dish } from '../shared/dish'
import { DishService } from '../services/dish.service';
import { Params, ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { switchMap } from 'rxjs/operators';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Comment } from '../shared/comment';



@Component({
  selector: 'app-dishdetail',
  templateUrl: './dishdetail.component.html',
  styleUrls: ['./dishdetail.component.scss']

})

export class DishdetailComponent implements OnInit {
  dish: Dish;
  dishIds: string[];
  prev: string;
  next: string;
  commentForm: FormGroup;
  commentNew: Comment;
  @ViewChild('cform') commentFormDirective;

  goBack(): void {
    this.location.back();
  }

  constructor(private dishservice: DishService,
    private route: ActivatedRoute,
    private location: Location, private fb: FormBuilder) { 
      this.createForm();
    }

    ngOnInit() {
      this.dishservice.getDishIds().subscribe(dishIds => this.dishIds = dishIds);
      this.route.params.pipe(switchMap((params: Params) => this.dishservice.getDish(params['id'])))
      .subscribe(dish => { this.dish = dish; this.setPrevNext(dish.id); });
    }

    formErrors = {
      'author': '',
      'comment': ''
    }

    validationMessages = {
      'author': {
        'required':      'Name is required.',
      'minlength':     'Name must be at least 2 characters long.',
      'maxlength':     'Name cannot be more than 25 characters long.'
      },
      'comment': {
        'required':      'Comment is required.',
      },
    }

    setPrevNext(dishId: string) {
      const index = this.dishIds.indexOf(dishId);
      this.prev = this.dishIds[(this.dishIds.length + index - 1) % this.dishIds.length];
      this.next = this.dishIds[(this.dishIds.length + index + 1) % this.dishIds.length];
    }

    createForm() {
      this.commentForm = this.fb.group({
        author: ['',[ Validators.required, Validators.minLength(2), Validators.maxLength(25)]],
        rating: 5,
        comment: ['', [Validators.required]]
      })
      this.commentForm.valueChanges.subscribe(data => this.onValueChanged(data));
      this.onValueChanged();
    }

    onSubmit() {
      this.commentForm.value['date'] = Date.now();
      this.commentNew = this.commentForm.value;
      this.dish.comments.push(this.commentNew);
      console.log('hi');
      this.commentFormDirective.resetForm();
      this.commentForm.reset({
        author: '',
        rating: 5,
        comment: '',
      });
    }

    onValueChanged(data?: any) {
      if (!this.commentForm) { return; }
      const form = this.commentForm;
      console.log(data);
      for (const field in this.formErrors) {
        if(this.formErrors.hasOwnProperty(field)) {
          this.formErrors[field] = '';
          const control = form.get(field);
          console.log('1');
        if (control && control.dirty && !control.valid) {
          const messages = this.validationMessages[field];
          console.log(messages);
          for (const key in control.errors) {
            if (control.errors.hasOwnProperty(key)) {
              this.formErrors[field] += messages[key] + ' ';
            }
          }
        }
        }
      }
    }
  openLoginForm() {
    console.log('hi');
  }


}