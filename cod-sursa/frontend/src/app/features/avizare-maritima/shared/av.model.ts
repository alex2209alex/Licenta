import { NgbDateStruct, NgbTimeStruct } from "@ng-bootstrap/ng-bootstrap";
import { Generic } from "../../shared/generic.model";

export class AvizareMaritima{
    id: number | null = null;
    estimatedArrivalDate: NgbDateStruct | null = null;
    estimatedArrivalTime: NgbTimeStruct | null = null;
    ship: Generic | null = null;
    port: Generic | null = null;
}