import { Injectable, Injector } from '@angular/core';
import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Observable } from 'rxjs';
import { TranslateService } from "@ngx-translate/core";

@Injectable()
export class LanguageInterceptor implements HttpInterceptor {

  constructor(private readonly injector: Injector) { }

  public intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    try {
      const translateService = this.injector.get(TranslateService)
      req = req.clone({
        setHeaders: { 'Accept-Language': translateService.currentLang }
      });
    } catch {
      req = req.clone({
        setHeaders: { 'Accept-Language': 'ro' }
      });
    }
    return next.handle(req);
  }
}
