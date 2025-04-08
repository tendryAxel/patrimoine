package school.hei.patrimoine.cas.example;

import static java.time.Month.AUGUST;
import static java.time.Month.DECEMBER;
import static java.time.Month.JULY;
import static java.time.Month.JUNE;
import static school.hei.patrimoine.modele.Argent.ariary;
import static school.hei.patrimoine.modele.Devise.MGA;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import school.hei.patrimoine.cas.Cas;
import school.hei.patrimoine.modele.Devise;
import school.hei.patrimoine.modele.Personne;
import school.hei.patrimoine.modele.possession.Compte;
import school.hei.patrimoine.modele.possession.Creance;
import school.hei.patrimoine.modele.possession.Dette;
import school.hei.patrimoine.modele.possession.FluxArgent;
import school.hei.patrimoine.modele.possession.Materiel;
import school.hei.patrimoine.modele.possession.Possession;

public class TianaCas extends Cas {
  private final Personne personne;

  public TianaCas(LocalDate ajd, LocalDate finSimulation, Personne possesseur) {
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
    var compteBancaire = new Compte("Compte bancaire", ajd, ariary(60_000_000));
    var terrainBatî = new Materiel("Terrain batî", ajd, ajd, ariary(100_000_000), .1);

    new FluxArgent("Dépenses mensuelles", compteBancaire, ajd, LocalDate.MAX, 1, ariary(-700_000));

    var debutProjet = LocalDate.of(2025, JUNE, 1);
    var finProjet = LocalDate.of(2025, DECEMBER, 31);
    new FluxArgent(
        "Dépenser projet entrepreneurial",
        compteBancaire,
        debutProjet,
        finProjet,
        5,
        ariary(-5_000_000));
    new FluxArgent(
        "Revenues brut de 10% (debut projet)",
        compteBancaire, debutProjet.minusMonths(1), debutProjet, 5, ariary(70_000_000 / 10));
    new FluxArgent(
        "Revenues brut de 90% (fin projet)",
        compteBancaire, finProjet.minusMonths(1), finProjet, 5, ariary(70_000_000 * 9 / 10));

    var prêtBancaire =
        new Dette("Prêt bancaire", LocalDate.of(2025, JULY, 27), ariary(-20_000_000));
    Set<Creance> rembourseraMensuel =
        nNextLocalDateAfter(LocalDate.of(2025, AUGUST, 27), 12)
            .map(
                localDate ->
                    new Creance(
                        String.format("Remboursement mensuel %s", localDate),
                        localDate,
                        ariary(2_000_000)))
            .collect(Collectors.toSet());

    var possessions = new HashSet<Possession>();
    possessions.addAll(Set.of(compteBancaire, terrainBatî, prêtBancaire));
    possessions.addAll(rembourseraMensuel);
    return possessions;
  }

  private Stream<LocalDate> nNextLocalDateAfter(LocalDate date, int n) {
    return IntStream.range(0, n).mapToObj(date::plusMonths);
  }

  @Override
  protected void init() {}

  @Override
  protected void suivi() {}
}
