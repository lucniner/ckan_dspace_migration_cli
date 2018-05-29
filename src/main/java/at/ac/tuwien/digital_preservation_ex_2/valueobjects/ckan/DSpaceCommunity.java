package at.ac.tuwien.digital_preservation_ex_2.valueobjects.ckan;

public class DSpaceCommunity {

    private Integer id;
    private String name;
    private String type;
    private String link;

    public DSpaceCommunity() {
    }

    public DSpaceCommunity(Integer id, String name, String type, String link) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.link = link;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DSpaceCommunity{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", type='").append(type).append('\'');
        sb.append(", link='").append(link).append('\'');
        sb.append('}');
        return sb.toString();
    }
}