import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {CategoriesComponent } from './components/categories/categories.component'
import { CityListComponent } from './components/city-list/city-list.component';

const routes: Routes = [
  { path: '' ,component:CategoriesComponent},
  {path:'app-categories',component: CategoriesComponent }, 
  {path:'city-list',component:CityListComponent}
  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
