package cn.rainspace.logistics.repository;

import java.util.List;

public interface Dao<T> {
    public int add(T t);

    public int delete(int id);

    public int update(T t);

    public T getById(int id);

    public List<T> getAll();
}
