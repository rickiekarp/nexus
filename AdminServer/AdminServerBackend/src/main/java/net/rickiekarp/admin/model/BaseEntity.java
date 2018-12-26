//package net.rickiekarp.admin.model;
//
//import javax.persistence.Column;
//import javax.persistence.GeneratedValue;
//import javax.persistence.Id;
//import javax.persistence.MappedSuperclass;
//
//@MappedSuperclass()
//public abstract class BaseEntity {
//
//    @Id
//    @GeneratedValue
//    @Column(name = "id")
//    private int id;
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    @Override
//    public String toString() {
//        return String.format("%s(id=%d)", this.getClass().getSimpleName(), this.getId());
//    }
//}
