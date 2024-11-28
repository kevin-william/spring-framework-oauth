package oauth2Jwt.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "TASK")
public class Task {

  private @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY) long id;
  private String nome;
  private String descricao;

  public Task(){}

  public Task(long id, String nome, String descricao) {
    this.id = id;
    this.nome = nome;
    this.descricao = descricao;
  }

  public String getDescricao() {
    return descricao;
  }

  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Task task = (Task) o;
    return getId() == task.getId() && Objects.equals(getNome(), task.getNome()) && Objects.equals(getDescricao(), task.getDescricao());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getNome());
  }

  @Override
  public String toString() {
    return "ID=" + id +
        ", NOME='" + nome + '\'' +
        ", DESCRICAO='" + descricao + '\'';
  }
}
