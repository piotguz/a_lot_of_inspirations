import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-categories',
  templateUrl: './categories.component.html',
  styleUrls: ['./categories.component.scss']
})
export class CategoriesComponent implements OnInit {
  ngOnInit() {
    
  }
      images = [
        { img: './assets/categories/top_intrests.jpg' },
        { img: '../assets/categories/sun_beach.jpg' },
        { img: '../assets/categories/family_holidays.jpg' },
        { img: '../assets/categories/city_brak.jpg'},
        { img: '../assets/categories/romantic_weekend.jpg'},
        
      ];
 }
  


