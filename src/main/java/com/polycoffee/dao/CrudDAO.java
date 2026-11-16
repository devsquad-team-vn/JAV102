package com.polycoffee.dao;

import java.util.List;

/**
 * Interface tong quat dinh nghia cac phuong thuc CRUD chuan cho tang DAO.
 * Cai dat theo chuan Slide 3 & Lab 3 - JAV102.
 *
 * @param <Entity> Kieu doi tuong Entity tuong ung voi bang trong CSDL
 * @param <ID> Kieu khoa chinh cua Entity (Integer hoac Long)
 * @author Quang (Nhóm PolyCoffee - JAV102)
 */
public interface CrudDAO<Entity, ID> {
    int create(Entity entity);

    int update(Entity entity);

    int delete(ID id);

    List<Entity> findAll();

    Entity findById(ID id);

    List<Entity> findBySql(String sql, Object... values);
}
