package com.PortaMauricio.best_travel.infraestructure.abstract_service;

public interface CrudService <RQ, RS, ID>{

    RS Create(RQ request);

    RS Read(ID id);

    RS Update(RQ request, ID id);

    void Delete(ID id);
}
