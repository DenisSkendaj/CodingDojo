package com.denis.projectmanager.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue (strategy =  GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Username is required!")
    @Size(min = 3, max=30,message = "Username has to be at least 3 characters")
    private String userName;

    @NotEmpty(message = "Email is required!")
    @Email(message = "Please enter a valid email!")
    private String email;

    @NotEmpty(message = "Password is required!")
    @Size(min = 8, max = 128, message = "Password must be between 8 and 128 characters!")
    @Pattern(regexp="^(?=.*[A-Z])(?=.*\\d).*$", message="Password must contain at least one capital letter and two numbers")
    private String password;

    @Transient
    @NotEmpty(message = "Confirm Password is required!")
    @Size(min = 8, max = 128, message = "Password must be between 8 and 128 characters!")
    private String confirm;

    @OneToMany(mappedBy = "teamLeader", fetch = FetchType.LAZY)
    private List<Project> projects;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "users_projects",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id")
    )
    private List<Project> particpatedProjects;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Task> tasks;

    @Column(updatable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createdAt;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date updatedAt;

    @PrePersist
    public void createOn(){
        this.createdAt = new Date();
    }

    @PreUpdate
    public void updateOn(){
        this.updatedAt = new Date();
    }

    public User(){

    }

    public User(Long id, String userName, String email, String password, String confirm, List<Project> projects, List<Project> particpatedProjects, List<Task> tasks) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.confirm = confirm;
        this.projects = projects;
        this.particpatedProjects = particpatedProjects;
        this.tasks = tasks;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
        for(Project p: this.projects){
            this.particpatedProjects.add(p);
        }
    }

    public List<Project> getParticpatedProjects() {
        return particpatedProjects;
    }

    public void setParticpatedProjects(List<Project> particpatedProjects) {
        this.particpatedProjects = particpatedProjects;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
