package rxdemo.prescription.validation;

import rxdemo.drug.OpiatesDrugConcept;
import rxdemo.prescription.Prescription;

public class OpiateDurationValidationRule implements IRxValidationRule {

   public static final String MESSAGE = "Prescibing opioids and benzodiazepines in quantities greater than a 30-day" +
      " supply is prohibited. Refills may be used to extend the intended duration of treatment.";

   // add free text dosing feature to make this more complex?

   @Override
   public RxValidationResult validateAgainstRule(Prescription rx) {
      RxValidationResult result = new RxValidationResult();
      if (isForOpioid(rx) && durationInvalidForOpioid(rx)) {
         result.addInvalidComponent(ERxComponent.DURATION);
         result.setMessage(MESSAGE);
      }
      return result;
   }

   private boolean durationInvalidForOpioid(Prescription rx) {
      return rx.getDurationAsDays() > 30 || rx.getDurationAsDays() == 0;
   }

   private boolean isForOpioid(Prescription rx) {
      return OpiatesDrugConcept.isDrugInConcept(rx.getDrug());
   }
}