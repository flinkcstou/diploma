import {Injectable} from '@angular/core';
import {BehaviorSubject} from "rxjs";
import {HttpService} from "./http.service";
import {AngularFireMessaging} from "@angular/fire/messaging";
import {filter, share, tap} from "rxjs/internal/operators";

export const FCM_REGISTRATION_ID = 'fcm_registration_id';

@Injectable()
export class MessagingService {

  currentMessage = new BehaviorSubject<any>(null);

  constructor(private http: HttpService,
              private angularFireMessaging: AngularFireMessaging) {

    this.angularFireMessaging.messaging.subscribe(
      (_messaging) => {
        _messaging.onMessage = _messaging.onMessage.bind(_messaging);
        _messaging.onTokenRefresh = _messaging.onTokenRefresh.bind(_messaging);
      }
    );

    this.receiveMessage().subscribe();
  }

  private receiveMessage() {
    return this.angularFireMessaging.messages.pipe(tap(message => {
      console.log("new message received. ", message);
      this.currentMessage.next(message);
    }));
  }

  onMessage() {
    return this.currentMessage.pipe(
      filter(message => !!message),
      share(),
    );
  }

  requestPermission() {
    return this.angularFireMessaging.requestToken.pipe(
      tap(registrationId => localStorage.setItem(FCM_REGISTRATION_ID, registrationId as string))
    );//.subscribe(registrationId => console.log('registrationId:', registrationId));
  }

  subscribe(topic) {
    let registrationId = localStorage.getItem(FCM_REGISTRATION_ID);

    this.http.post("/notification/subscribe", {registrationId: registrationId, topic: topic})
      .pipe(tap(_ => console.log(`subscribed to ${topic}`)))
      .subscribe();
  }

  unsubscribe(topic) {
    let registrationId = localStorage.getItem(FCM_REGISTRATION_ID);

    this.http.get("/notification/unsubscribe", {registrationId: registrationId, topic: topic})
      .pipe(tap(_ => console.log(`unsubscribed from ${topic}`)))
      .subscribe();
  }

}
