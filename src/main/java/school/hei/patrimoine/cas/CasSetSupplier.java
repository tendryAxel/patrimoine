package school.hei.patrimoine.cas;

import static java.time.Month.APRIL;
import static java.time.Month.MARCH;
import static school.hei.patrimoine.modele.Argent.ariary;

import java.time.LocalDate;
import java.util.Set;
import java.util.function.Supplier;
import school.hei.patrimoine.cas.example.TianaCas;
import school.hei.patrimoine.modele.Personne;

public class CasSetSupplier implements Supplier<CasSet> {
  @Override
  public CasSet get() {
    var tianaCas =
        new TianaCas(
            LocalDate.of(2025, APRIL, 8), LocalDate.of(2026, MARCH, 31), new Personne("Tiana"));

    return new CasSet(Set.of(tianaCas), ariary(193_080_821));
  }
}
