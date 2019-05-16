import {Component, ElementRef, Inject, OnInit, Renderer2} from '@angular/core';
import {LanguagesService} from "../shared/languages.service";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {BookingService} from "../shared/booking.service";

@Component({
  selector: 'app-table-selection',
  templateUrl: './table-selection.component.html',
  styleUrls: ['./table-selection.component.css']
})
export class TableSelectionComponent implements OnInit {

  public selectedItem: any = {};
  public selectedId: number

  constructor(@Inject(MAT_DIALOG_DATA) public data,
              public dialogRef: MatDialogRef<TableSelectionComponent>,
              private bookingService: BookingService,
              public languagesService: LanguagesService,
              private el: ElementRef,
              private renderer: Renderer2
  ) {
  }

  public staticTable = {
    "table-part-left": [
      {
        name: 'table-one',
        enable: false,
      },
      {
        name: 'table-two',
        enable: false,

      },
      {
        name: 'table-three',
        enable: false,
      },
      {
        name: 'table-four',
        enable: false,
      },
    ],
    "table-part-center-left": [
      {
        name: 'table-five',
        enable: false,
      },
      {
        name: 'table-six',
        enable: false,

      },
      {
        name: 'table-seven',
        enable: false,
      },
    ],
    "table-part-center": [
      {
        name: 'table-eight',
        enable: false,
      },
      {
        name: 'table-nine',
        enable: false,
      },
      {
        name: 'table-ten',

        enable: false,
      },
      {
        name: 'table-eleven',
        enable: false,

      },
    ],
    "table-part-up": [
      {
        name: 'table-twelve',
        enable: false,

      },
      {
        name: 'table-thirteen',
        enable: false,

      },
    ],
    "table-part-right": [
      {
        name: 'table-fourteen',
        enable: false,

      },
      {
        name: 'table-fifteen',
        enable: false,

      },
      {
        name: 'table-sixteen',
        enable: false,

      },
      {
        name: 'table-seventeen',
        enable: false,

      },
    ],

  }


  keys(): Array<string> {
    return Object.keys(this.staticTable);
  }

  createstaticTable() {
    let count = 0;

    for (let key in this.staticTable) {
      for (let item in this.staticTable[key]) {
        this.staticTable[key][item].id = count++
        if(count%2==0)
        this.staticTable[key][item].enable=true;
      }
    }
  }

  ngOnInit() {
    this.createstaticTable()
  }


  chooseTable(item: any) {
    this.selectedItem = item;
    if (this.selectedItem.enable) {

      setTimeout(() => {
        this.dialogRef.close(this.selectedItem.id)

      }, 200)
    }
  }

}
