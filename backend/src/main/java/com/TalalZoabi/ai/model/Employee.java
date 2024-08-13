package com.TalalZoabi.ai.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;


@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    @NotBlank(message = "First name is mandatory")
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @NotBlank(message = "Last name is mandatory")
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    private String email;

    @Column(name = "favorite_wiki_page")
    private String favoriteWikiPage;
    
    public Employee() {
    }

    public Employee(Long id, String firstName, String lastName, String email, String favoriteWikiPage) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.favoriteWikiPage = favoriteWikiPage;
    }

    public Employee(String firstName, String lastName, String email, String favoriteWikiPage) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.favoriteWikiPage = favoriteWikiPage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFavoriteWikiPage() {
        return favoriteWikiPage;
    }

    public void setFavoriteWikiPage(String favoriteWikiPage) {
        this.favoriteWikiPage = favoriteWikiPage;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstname='" + firstName + '\'' +
                ", lastname='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", favoriteWikiPage='" + favoriteWikiPage + '\'' +
                '}';
    }    

}
