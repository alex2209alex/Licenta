import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { HTTP_INTERCEPTORS, HttpBackend, HttpClient, HttpClientModule } from "@angular/common/http";
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { TranslateLoader, TranslateModule } from '@ngx-translate/core';
import { TranslateHttpLoader } from "@ngx-translate/http-loader";
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { LanguageInterceptor } from "./interceptors/language/language.interceptor";

export function HttpLoaderFactory(httpHandler: HttpBackend) {
  return new TranslateHttpLoader(new HttpClient(httpHandler));
}

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpBackend],
      },
      defaultLanguage: 'ro'
    }),
    NgbModule
  ],
  providers: [
    {provide: HTTP_INTERCEPTORS, useClass: LanguageInterceptor, multi: true}
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
