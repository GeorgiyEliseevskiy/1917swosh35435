import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  form!: FormGroup;

  constructor(private formBuilder: FormBuilder,
              private router: Router
  ) {
  }

  ngOnInit() {
  }

  onSubmit() {
  }

  toPage(link: string) {
    this.router.navigate([link]);
  }
}
