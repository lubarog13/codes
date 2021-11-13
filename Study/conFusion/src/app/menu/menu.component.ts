import { Component, OnInit, Inject } from '@angular/core';
import { Dish } from '../shared/dish';
import { DishService } from '../services/dish.service';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.scss']
})
export class MenuComponent implements OnInit {
  dishes: Dish[];

  selectedDish: Dish;

  constructor(private dishService: DishService, @Inject('BaseURL') public BaseURL) { }
  
  ngOnInit() {
    this.dishService.getDishes().subscribe(dishes => this.dishes = dishes);
    console.log(this.BaseURL)
  }
  onSelect(dish: Dish){
    this.selectedDish = dish
  }
}