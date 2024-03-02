import { Generic } from "../../shared/generic.model";
import { DeclaredCargo } from "./declared-cargo.model";

export class AvizareMaritima {
  id: number | null = null;
  estimatedArrivalDateTime: string | null = null;
  ship: Generic | null = null;
  port: Generic | null = null;
  agent: Generic | null = null;
  cargos: DeclaredCargo[] = [];
  rejectionReason: string | null = null;
  status: number | null = null;
}
