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
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaQuery;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

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
    public Usuarios findUser(Usuarios usuario) {
         EntityManager em = null;
        try {
            em = getEntityManager();
            if (em != null) {
                Query q = em.createNamedQuery("Usuarios.findCreatedUser");
                q.setParameter("email", usuario.getEmail());
                q.setParameter("contrasena", usuario.getContrasena());
                return (Usuarios)q.getSingleResult();
            }
        } catch (Exception e) {
            throw new IllegalStateException("Error al aceder a los datos", e);
        }
        return null;
    }
    
}
