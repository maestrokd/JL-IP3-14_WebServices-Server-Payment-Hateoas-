package ua.com.univerpulse.model.entities;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Role {

    // Fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name", length = 50)
    private String name;


//    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE, CascadeType.REFRESH}, orphanRemoval = true, mappedBy = "role")
//    private List<RoleUser> roleUserList = new ArrayList<>();
//
//    public void addUser(User user) {
//        RoleUser roleUser = new RoleUser();
//        roleUser.setUser(user);
//        roleUser.setRole(this);
//        roleUserList.add(roleUser);
//        user.getRoleUserList().add(roleUser);
//    }

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_user_role"))
//    private User user;



    // Getter and Setter


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public List<RoleUser> getRoleUserList() {
//        return roleUserList;
//    }
//
//    public void setRoleUserList(List<RoleUser> roleUserList) {
//        this.roleUserList = roleUserList;
//    }


//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }
}
