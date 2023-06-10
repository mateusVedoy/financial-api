package finances.api.infraestructure.postgres.model;

import javax.persistence.*;

@Entity
@Table(name = "operationtype")
public class OperationTypeModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ope_code")
    private Long id;
    @Column(name = "ope_description")
    private String description;

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
}
