/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cinewebbackend.control;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public abstract class AbstractBean<T> implements Serializable {

    final Class tipoDato;

    public AbstractBean(Class tipoDato) {
        this.tipoDato = tipoDato;
    }

    public abstract EntityManager getEntityManager();

    public void Create(T registro) throws IllegalStateException, IllegalArgumentException {
        EntityManager em = getEntityManager();
        if (registro != null && em != null) {
            try {
                em.persist(registro);
                return;
            } catch (Exception e) {
                throw new IllegalStateException("Error al persistir dato", e);
            }
        }
        throw new IllegalArgumentException("El registro o el EntityManager son nulos");
    }

    public T Modify(T registroNuevo) throws IllegalStateException, IllegalArgumentException {
        EntityManager em = getEntityManager();

        if (registroNuevo != null && em != null) {
            try {
                return em.merge(registroNuevo);
            } catch (Exception e) {
                throw new IllegalStateException("Error al modificar dato", e);
            }
        }
        throw new IllegalArgumentException("El registro o el EntityManager son nulos");
    }

    public void Delete(T registroBorrar) throws IllegalStateException, IllegalArgumentException {
        EntityManager em = getEntityManager();
        if (registroBorrar != null && em != null) {
            try {
                if (em.contains(registroBorrar)) {
                    em.remove(registroBorrar);
                    return;
                }
            } catch (Exception e) {
                throw new IllegalStateException("Error al eliminar dato", e);
            }
        }
        throw new IllegalArgumentException("El registro o el EntityManager son nulos");
    }

    public List<T> FindAll() throws IllegalStateException, IllegalArgumentException {
        EntityManager em = getEntityManager();
        List<T> registros = Collections.EMPTY_LIST;
        if (em != null) {
            try {
                CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
                cq.select(cq.from(tipoDato));
                Query q = em.createQuery(cq);
                return q.getResultList();
            } catch (Exception e) {
                throw new IllegalStateException("Error al aceder a los datos", e);
            }
        }
        throw new IllegalArgumentException("El registro o el EntityManager son nulos");
    }

    public T FindById(Object id) throws IllegalStateException, IllegalArgumentException {
        EntityManager em = getEntityManager();
        List<T> registros = Collections.EMPTY_LIST;
        if (em != null && id != null) {
            try {
                return (T) em.find(tipoDato, id);
            } catch (Exception e) {
                throw new IllegalStateException("Error al aceder a los datos", e);
            }
        }
        throw new IllegalArgumentException("El registro o el EntityManager son nulos");
    }

    public List<T> FindRange(int first, int pageSize) throws IllegalStateException, IllegalArgumentException {

        EntityManager em = getEntityManager();

        if (first >= 0 && pageSize > 0 && em != null) {
            try {
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
                throw new IllegalStateException("Error al aceder a los datos", ex);
            }
        }
        throw new IllegalArgumentException();

    }

    public int count() throws IllegalStateException, IllegalArgumentException {
        EntityManager em = getEntityManager();
        if (em != null) {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Long> cq = cb.createQuery(Long.class);
            cq.select(cb.count(cq.from(tipoDato)));
            return em.createQuery(cq).getSingleResult().intValue();
        }
        throw new IllegalStateException();
    }

}
