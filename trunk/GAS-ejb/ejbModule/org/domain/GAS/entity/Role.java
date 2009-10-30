package org.domain.GAS.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.security.management.RoleName;

@Entity
@Table(name = "role",  catalog = "database_gas")
@Name(value="newRole")
public class Role implements Serializable
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -593776597603673863L;
	// seam-gen attributes (you should probably edit these)
    private Long id;
    private Account account;
    private String name;

    // add additional entity attributes

    // seam-gen attribute getters/setters with annotations (you probably should edit)

    @Id @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Username", nullable = false)
	@NotNull
	public Account getAccount() {
		return this.account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	@RoleName
    @Column(name = "name", nullable = false)
    @Length(max = 100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
