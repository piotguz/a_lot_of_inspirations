import { Component, OnInit } from '@angular/core';
import airports from '../../../assets/airport-locations/airports.json';
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { MatDatepickerInputEvent } from '@angular/material';
import { all } from 'q';
import { FormControl } from '@angular/forms';

@Component({
  selector: 'app-city-list',
  templateUrl: './city-list.component.html',
  styleUrls: ['./city-list.component.scss']
})
export class CityListComponent implements OnInit {

  tiles:any  = airports;
  departureDate: Date;
  returnDate: Date;
  allCities = [];
  panelColor = new FormControl('blue');
  selectedValue:string;

  constructor(private http: HttpClient) { }

  ngOnInit() {
    this.getAllCities();
    
  }

  addDepartureDate(event: MatDatepickerInputEvent<Date>) {
    this.departureDate =  event.value;
  }
  addReturnDate(event: MatDatepickerInputEvent<Date>) {
    this.returnDate =  event.value;
  }

  getAllCities() {   
    for(let country of this.tiles){
      for(let city of country.cities){
        this.allCities.push(city);
      }
    }
  }
  getFromCity() {

  }
  getSelectedCities() {
    let selectedCities = [];
    for(let country of this.tiles){
      for(let city of country.cities){
        if(city.checked){
          selectedCities.push(city.iata);
        }
      }
  }
  return selectedCities;
  }

  postConnections(){
    
    const body = JSON.stringify({
      "targetCities": this.getSelectedCities(),
      "fromcity":this.selectedValue,
      "departureDate":this.departureDate,
      "returnDate":this.returnDate,
      "market":"PL",
      "language":"EN",
      "passengers":1

    });
    console.log(body)
    return this.http.post("https://insp1.herokuapp.com/connections", body, 
    { headers : new HttpHeaders({'Content-Type': 'application/json'}), withCredentials: false, observe: 'response' }).toPromise()
    .then(
      response => {
        console.log(response)
          if (response.status === 200) {
              return "";
          }
      }, 
    )                   
  }
}