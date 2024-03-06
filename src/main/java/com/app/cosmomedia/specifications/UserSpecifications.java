package com.app.cosmomedia.specifications;

import com.app.cosmomedia.entity.Users;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;

public class UserSpecifications {

    public static Specification<Users> filterByFirstName(String firstName) {
        return (root, query, builder) ->
                firstName == null ? null : builder.like(builder.function("LOWER", String.class, root.get("firstName")), "%" + firstName.toLowerCase() + "%");
    }

    public static Specification<Users> filterByLastName(String lastName) {
        return (root, query, builder) ->
                lastName == null ? null : builder.like(builder.function("LOWER", String.class, root.get("lastName")), "%" + lastName.toLowerCase() + "%");
    }

    public static Specification<Users> filterByDateRange(Date startDate, Date endDate) {
        return (root, query, builder) ->
                (startDate == null || endDate == null) ? null : builder.between(root.get("createdAt"), startDate, endDate);
    }

    public static Specification<Users> filterByEmail(String email) {
        return (root, query, builder) ->
                email == null ? null : builder.like(builder.function("LOWER", String.class, root.get("email")), "%" + email.toLowerCase() + "%");
    }

    public static Specification<Users> filterByRole(String role) {
        return (root, query, builder) ->
                role == null ? null : builder.equal(builder.function("LOWER", String.class, root.get("role")), role.toLowerCase());
    }

    public static Specification<Users> filterByDeletedBy(String deletedBy) {
        return (root, query, builder) ->
                deletedBy == null ? null : builder.equal(builder.function("LOWER", String.class, root.get("deletedBy")), deletedBy.toLowerCase());
    }
}