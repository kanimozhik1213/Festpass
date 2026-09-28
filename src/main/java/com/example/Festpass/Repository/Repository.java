package com.example.Festpass.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Festpass.Model.Modelclass;

public interface Repository extends JpaRepository<Modelclass, Long> {

}