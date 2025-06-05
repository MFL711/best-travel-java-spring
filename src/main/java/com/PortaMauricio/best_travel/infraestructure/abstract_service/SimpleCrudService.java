package com.PortaMauricio.best_travel.infraestructure.abstract_service;

/*Cramos otro crud ya que el metodo update será diferente*/
public interface SimpleCrudService <RQ, RS, ID>{

    RS create (RQ request);

    RS read (ID id);

    void delete (ID id);

}
