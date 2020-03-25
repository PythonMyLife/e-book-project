package backend.Entity;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;

@Entity
@Table(name = "users", schema = "ebook", catalog = "")
@JsonIgnoreProperties(value = {"handler", "hibernateLazyInitializer", "fieldHandler"})
public class User {
    @Id
    @JsonBackReference
    @JsonManagedReference
    private String username;
    private String password;
    private String email;
    private Integer status;  //0未禁用，1被禁用
    private Integer identity; //0普通用户，1管理员

    @Basic
    @Column(name = "username")
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }

    @Basic
    @Column(name = "identity")
    public Integer getIdentity() {
        return identity;
    }
    public void setIdentity(Integer identity) {
        this.identity = identity;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }

        User that = (User) o;

        if(!username.equals(that.username)) {
            return false;
        }
        if(!password.equals(that.password)) {
            return false;
        }
        if(!email.equals(that.email)) {
            return false;
        }
        if(!status.equals(that.status)) {
            return false;
        }
        if(!identity.equals(that.identity)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode(){
        int result = 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (identity != null ? identity.hashCode() : 0);
        return result;
    }

}
