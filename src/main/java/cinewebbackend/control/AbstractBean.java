/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cinewebbackend.control;

import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractBean<T> implements Serializable {

    final Class tipoDato;

    public AbstractBean(Class tipoDato) {
        this.tipoDato = tipoDato;
    }

    public abstract EntityManager getEntityManager();

    public void Create(T registro) throws IllegalStateException, IllegalArgumentException {

        EntityManager em = null;
        if (registro != null) {
            em = getEntityManager();
            try {
                if (em != null) {
                    em.persist(registro);
                    return;
                }
            } catch (Exception e) {
                throw new IllegalStateException("Error al persistir dato", e);
            }
        }
        throw new IllegalArgumentException("El registro o el EntityManager son nulos");
    }

    public T Modify(T registroNuevo) throws IllegalStateException, IllegalArgumentException {
        EntityManager em = null;

        if (registroNuevo != null) {
            em = getEntityManager();
            if (em != null) {
                try {
                    return em.merge(registroNuevo);
                } catch (Exception e) {
                    throw new IllegalStateException("Error al modificar dato", e);
                }
            }
        }
        throw new IllegalArgumentException("El registro o el EntityManager son nulos");
    }

    public void Delete(T registroBorrar) throws IllegalStateException, IllegalArgumentException {
        EntityManager em = null;
        System.out.println(registroBorrar);
        if (registroBorrar != null) {
            try {
                em = getEntityManager();
                if (em != null) {
                    if (!em.contains(registroBorrar)) {
                        registroBorrar = em.merge(registroBorrar);
                    }
                    em.remove(registroBorrar);
                    return;
                } else {
                    System.out.println("hola");
                }
            } catch (Exception e) {
                throw new IllegalStateException("Error al eliminar dato", e);
            }
        }
        throw new IllegalArgumentException("El registro o el EntityManager son nulos");
    }

    public List<T> FindAll() throws IllegalStateException, IllegalArgumentException {
        EntityManager em = null;
        List<T> registros = Collections.EMPTY_LIST;
        try {
            em = getEntityManager();
            if (em != null) {

                CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
                cq.select(cq.from(tipoDato));
                Query q = em.createQuery(cq);
                return q.getResultList();
            }
        } catch (Exception e) {
            throw new IllegalStateException("Error al aceder a los datos", e);
        }
        return registros;
    }

    public T FindById(Object id) throws IllegalStateException, IllegalArgumentException {
        EntityManager em = null;
        if (id != null) {
            try {
                em = getEntityManager();
                if (em != null) {
                    return (T) em.find(tipoDato, id);
                }
            } catch (Exception e) {
                throw new IllegalStateException("Error al aceder a los datos", e);
            }
        }
        throw new IllegalArgumentException("El registro o el EntityManager son nulos");
    }

    public List<T> FindRange(int first, int pageSize) throws IllegalStateException {

        EntityManager em = null;

        if (first >= 0 && pageSize > 0) {

            try {
                em = getEntityManager();
                if (em != null) {
                    CriteriaBuilder cb = em.getCriteriaBuilder();
                    CriteriaQuery cq = cb.createQuery(tipoDato);
                    Root<T> raiz = cq.from(tipoDato);
                    cq.select(raiz);
                    TypedQuery q = em.createQuery(cq);

                    q.setFirstResult(first);
                    q.setMaxResults(pageSize);
                    return q.getResultList();

                }

            } catch (Exception ex) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);

            }
            throw new IllegalStateException();

        }
        return Collections.EMPTY_LIST;

    }

    public int count() throws IllegalStateException, IllegalArgumentException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            if (em != null) {
                CriteriaBuilder cb = em.getCriteriaBuilder();
                CriteriaQuery<Long> cq = cb.createQuery(Long.class);
                cq.select(cb.count(cq.from(tipoDato)));
                return em.createQuery(cq).getSingleResult().intValue();
            }
        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        throw new IllegalStateException();
    }

}
