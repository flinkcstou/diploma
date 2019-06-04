import {Component, Inject, OnInit} from '@angular/core';
import {OrderService} from "../orders/shared/order.service";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {LanguagesService} from "../shared/languages.service";
import {ItemService} from "../orders/shared/item.service";
import {DOCUMENT} from "@angular/platform-browser";
import {Comments} from "../../model/comments.model";
import {LoginService} from "../login/login.service";
import {CommentsLike} from "../../model/comments-like.model";
import {CommentsWithExtendsModel} from "../../model/commentsWithExtends.model";

@Component({
  selector: 'app-comments',
  templateUrl: './comments.component.html',
  styleUrls: ['./comments.component.css']
})
export class CommentsComponent implements OnInit {

  constructor(@Inject(MAT_DIALOG_DATA) public data,
              public dialogRef: MatDialogRef<CommentsComponent>,
              private itemService: ItemService,
              private orderService: OrderService,
              public languagesService: LanguagesService,
              @Inject(DOCUMENT) private document: Document,
              public loginService: LoginService) {
  }

  windowScrolled: boolean;
  public ngComments: Comments;
  public ngCommentsLike: Array<CommentsLike>;
  public commentsWithExtendsModels: Array<CommentsWithExtendsModel>;

  public comments: Array<Comments>;

  public itemId: number;
  public isValidate: boolean = false;
  public isCommentMessage: boolean = false;

  ngOnInit() {
    this.resetComments();
    this.resetNgComments();
    this.getCommentsByPerson();
  }

  onSubmit() {
    if (this.ngComments.messages.length && this.isValidate) {
      this.orderService.setComments(this.ngComments).then(value => {
          this.updateCommentsByServer();
          this.resetNgComments();
        }
      )
    }
  }


  updateCommentsByServer() {
    this.orderService.getCommentsbyItemId(this.itemId).then(value => {
      this.comments = value.body as Comments[];
      this.filterComments();
      this.getCommentsByPerson();
    });
  }

  filterComments() {
    if (!this.comments.length) {
    }
    else {
      this.comments = this.comments.map((value, index) => {
        value.dateFormatter = this.formatDate(value.date);
        if (!value.liked) {
          value.liked = null;
        }
        if (!value.disliked) {
          value.disliked = null;
        }
        return value;
      });
      this.comments.sort(function (a, b) {
        let keyA = new Date(a.date),
          keyB = new Date(b.date);
        if (keyA < keyB) return 1;
        if (keyA > keyB) return -1;
        return 0;
      });
    }

  }

  resetComments() {
    this.itemId = this.data.itemId;
    this.isCommentMessage = this.data.isCommentsMessage;
    this.comments = this.data.comments as Array<Comments>;
    this.filterComments();

  }

  resetNgComments() {
    this.ngComments = new Comments();
    this.ngComments = {
      messages: '',
      date: new Date(),
      personName: 'default',
      itemId: this.itemId,
      dateFormatter: 'default',
      id: 0,
      itemName: 'default',
      personId: this.getPersonId(),
      liked: 0,
      disliked: 0,
    }
  }

  getPersonId() {
    if (this.loginService.personDisplay && this.loginService.personDisplay.username) {
      return this.loginService.personDisplay.username
    }
    else {
      let person = JSON.parse(localStorage.getItem('person'));
      return person.id
    }
  }

  formatDate(date) {
    date = new Date(date);
    let monthNames = [
      "January", "February", "March",
      "April", "May", "June", "July",
      "August", "September", "October",
      "November", "December"
    ];

    let minutes = date.getMinutes();
    let hours = date.getHours();

    if (hours < 10) {
      hours = '0' + hours;
    }
    else {
      hours = '' + hours;
    }
    if (minutes < 10) {
      minutes = '0' + minutes;
    }
    else {
      minutes = '' + minutes;
    }


    let day = date.getDate();
    let monthIndex = date.getMonth();
    let year = date.getFullYear();

    return hours + ':' + minutes + '  ' + day + ' ' + monthNames[monthIndex] + ' ' + year;
  }

  scrollDown() {
    event.preventDefault()
    document.getElementById('downpage').scrollIntoView();
    document.getElementById('commentText').focus();

  }

  addLiked(comments: Comments) {
    let commentsLike: CommentsLike = new CommentsLike();
    commentsLike.personId = this.getPersonId();
    commentsLike.commentsId = comments.id;
    commentsLike.liked = 1;
    commentsLike.disliked = null;
    this.orderService.setCommentsLike(commentsLike).then(value => {
      this.updateCommentsByServer();
    })
  }

  addDisliked(comments: Comments) {
    let commentsLike: CommentsLike = new CommentsLike();
    commentsLike.personId = this.getPersonId();
    commentsLike.commentsId = comments.id;
    commentsLike.liked = null;
    commentsLike.disliked = 1;
    this.orderService.setCommentsLike(commentsLike).then(value => {
      this.updateCommentsByServer();
    })
  }

  getCommentsByPerson() {
    this.orderService.setCommentsLikeByPersonid(this.getPersonId()).then(value => {
      this.ngCommentsLike = value.body as Array<CommentsLike>;
      this.ngCommentsLike = this.ngCommentsLike.filter((value, index) => {
        let nulls = (value.disliked == null && value.liked == null)
        return !nulls
      })
      this.commentsWithExtendsModels = this.comments.map((value, index) => {
        let comme = new CommentsWithExtendsModel();
        comme = {
          ...value,
          isLiked: false,
          isDisliked: false
        };
        this.ngCommentsLike.map((value2, index2) => {
          if (value.id == value2.commentsId) {
            comme.isLiked = !!value2.liked
            comme.isDisliked = !!value2.disliked
          }
        });
        return comme
      });
      console.table(this.ngCommentsLike)
      console.table(this.commentsWithExtendsModels)
    })
  }

  checkValues(e) {
    let checked = e.currentTarget.value.replace(/\s/g, '');
    if (!!checked && checked.length > 10) {
      this.isValidate = true;
    }
    else {
      this.isValidate = false;
    }
  }

}
