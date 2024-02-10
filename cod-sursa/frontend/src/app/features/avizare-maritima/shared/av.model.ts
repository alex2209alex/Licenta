import { Generic } from "../../shared/generic.model";

export class AvizareMaritima {
  id: number | null = null;
  estimatedArrivalDateTime: string | null = null;
  ship: Generic | null = null;
  port: Generic | null = null;
  agent: Generic | null = null;
}
