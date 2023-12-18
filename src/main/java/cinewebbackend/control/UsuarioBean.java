/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cinewebbackend.control;

import cinewebbackend.entities.Usuarios;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.io.Serializable;

/**
 *
 * @author mjlopez
 */
@Stateless
@LocalBean

public class UsuarioBean extends AbstractBean<Usuarios> implements Serializable{
    @PersistenceContext(unitName = "cinePU")
    EntityManager em;

    public UsuarioBean() {
        super(Usuarios.class);
    }
    @Override
    public EntityManager getEntityManager() {
        return em;
    }
    
}
