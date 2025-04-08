package school.hei.patrimoine.cas.example;

import static school.hei.patrimoine.modele.Argent.ariary;
import static school.hei.patrimoine.modele.Devise.MGA;

import java.time.LocalDate;
import java.util.Set;
import school.hei.patrimoine.cas.Cas;
import school.hei.patrimoine.modele.Devise;
import school.hei.patrimoine.modele.Personne;
import school.hei.patrimoine.modele.possession.Compte;
import school.hei.patrimoine.modele.possession.FluxArgent;
import school.hei.patrimoine.modele.possession.Materiel;
import school.hei.patrimoine.modele.possession.Possession;
import school.hei.patrimoine.modele.possession.TransfertArgent;

public class BakoCas extends Cas {
  private final Personne personne;

  public BakoCas(LocalDate ajd, LocalDate finSimulation, Personne possesseur) {
    super(ajd, finSimulation, possesseur);
    personne = possesseur;
  }

  @Override
  protected Devise devise() {
    return MGA;
  }

  @Override
  protected String nom() {
    return String.format("%s", personne.nom());
  }

  @Override
  public Set<Possession> possessions() {
    var compteCourantBNI = new Compte("Courant BNI", ajd, ariary(2_000_000));
    var compteEpargneBMOI = new Compte("BMOI épragne", ajd, ariary(625_000));
    var coffreFortMaison = new Compte("Coffre fort à la maison", ajd, ariary(1_750_000));

    new FluxArgent("Salaire du CDI", compteCourantBNI, ajd, LocalDate.MAX, 2, ariary(2_125_000));
    new TransfertArgent(
        "Virement mensuel vers épragne",
        compteCourantBNI,
        compteEpargneBMOI,
        ajd,
        LocalDate.MAX,
        3,
        ariary(200_000));

    new FluxArgent("Colocation", compteCourantBNI, ajd, LocalDate.MAX, 26, ariary(-600_000));
    new FluxArgent(
        "Nourriture et transport", compteCourantBNI, ajd, LocalDate.MAX, 1, ariary(-700_000));

    var ordinateur = new Materiel("ordinateur portable", ajd, ajd, ariary(3_000_000), -.12);

    return Set.of(compteCourantBNI, compteEpargneBMOI, coffreFortMaison, ordinateur);
  }

  @Override
  protected void init() {}

  @Override
  protected void suivi() {}
}
