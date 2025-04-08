package school.hei.patrimoine.cas;

import static java.time.temporal.TemporalAdjusters.lastDayOfYear;
import static school.hei.patrimoine.modele.Argent.ariary;

import java.time.LocalDate;
import java.util.Set;
import java.util.function.Supplier;
import school.hei.patrimoine.cas.example.BakoCas;
import school.hei.patrimoine.modele.Personne;

public class CasSetSupplier implements Supplier<CasSet> {
  @Override
  public CasSet get() {
    var mardi8avril2025 = LocalDate.of(2025, 4, 8);
    var bakoCas =
        new BakoCas(mardi8avril2025, mardi8avril2025.with(lastDayOfYear()), new Personne("Bako"));

    return new CasSet(Set.of(bakoCas), ariary(13_111_657));
  }
}
