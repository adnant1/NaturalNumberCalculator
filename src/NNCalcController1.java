import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber2;

/**
 * Controller class.
 *
 * @author Put your name here
 */
public final class NNCalcController1 implements NNCalcController {

    /**
     * Model object.
     */
    private final NNCalcModel model;

    /**
     * View object.
     */
    private final NNCalcView view;

    /**
     * Useful constants.
     */
    private static final NaturalNumber TWO = new NaturalNumber2(2),
            INT_LIMIT = new NaturalNumber2(Integer.MAX_VALUE);

    /**
     * Updates this.view to display this.model, and to allow only operations
     * that are legal given this.model.
     *
     * @param model
     *            the model
     * @param view
     *            the view
     * @ensures [view has been updated to be consistent with model]
     */
    private static void updateViewToMatchModel(NNCalcModel model,
            NNCalcView view) {

        //collect info from model
        NaturalNumber top = model.top();
        NaturalNumber bottom = model.bottom();

        //update view using that information
        view.updateTopDisplay(top);
        view.updateBottomDisplay(bottom);

        //update subtract,divide,root,power buttons
        if (top.compareTo(bottom) >= 0) {
            view.updateSubtractAllowed(true);
        } else {
            view.updateSubtractAllowed(false);
        }

        if (bottom.isZero()) {
            view.updateDivideAllowed(false);
        } else {
            view.updateDivideAllowed(true);
        }

        if ((bottom.compareTo(TWO) >= 0) && (bottom.compareTo(INT_LIMIT) < 0)) {
            view.updateRootAllowed(true);
        } else {
            view.updateRootAllowed(false);
        }

        if (bottom.compareTo(INT_LIMIT) < 0) {
            view.updatePowerAllowed(true);
        } else {
            view.updatePowerAllowed(false);
        }
    }

    /**
     * Constructor.
     *
     * @param model
     *            model to connect to
     * @param view
     *            view to connect to
     */
    public NNCalcController1(NNCalcModel model, NNCalcView view) {
        this.model = model;
        this.view = view;
        updateViewToMatchModel(model, view);
    }

    @Override
    public void processClearEvent() {
        /*
         * Get alias to bottom from model
         */
        NaturalNumber bottom = this.model.bottom();
        /*
         * Update model in response to this event
         */
        bottom.clear();
        /*
         * Update view to reflect changes in model
         */
        updateViewToMatchModel(this.model, this.view);
    }

    @Override
    public void processSwapEvent() {
        /*
         * Get aliases to top and bottom from model
         */
        NaturalNumber top = this.model.top();
        NaturalNumber bottom = this.model.bottom();
        /*
         * Update model in response to this event
         */
        NaturalNumber temp = top.newInstance();
        temp.transferFrom(top);
        top.transferFrom(bottom);
        bottom.transferFrom(temp);
        /*
         * Update view to reflect changes in model
         */
        updateViewToMatchModel(this.model, this.view);
    }

    @Override
    public void processEnterEvent() {

        //create aliases
        NaturalNumber top = this.model.top();
        NaturalNumber bottom = this.model.bottom();

        //make top the bottom
        top.copyFrom(bottom);

        updateViewToMatchModel(this.model, this.view);

    }

    @Override
    public void processAddEvent() {

        //create aliases
        NaturalNumber top = this.model.top();
        NaturalNumber bottom = this.model.bottom();

        //add
        bottom.add(top);
        top.clear();

        updateViewToMatchModel(this.model, this.view);
    }

    @Override
    public void processSubtractEvent() {

        //create aliases
        NaturalNumber top = this.model.top();
        NaturalNumber bottom = this.model.bottom();

        //subtract
        bottom.subtract(top);
        top.clear();

        updateViewToMatchModel(this.model, this.view);

    }

    @Override
    public void processMultiplyEvent() {

        //create aliases
        NaturalNumber top = this.model.top();
        NaturalNumber bottom = this.model.bottom();

        //multiply
        bottom.multiply(top);
        top.clear();

        updateViewToMatchModel(this.model, this.view);

    }

    @Override
    public void processDivideEvent() {

        //create aliases
        NaturalNumber top = this.model.top();
        NaturalNumber bottom = this.model.bottom();

        //calculate divide while also making the top the remiander
        NaturalNumber rem = top.divide(bottom);
        bottom.copyFrom(top);
        top.copyFrom(rem);

        updateViewToMatchModel(this.model, this.view);

    }

    @Override
    public void processPowerEvent() {

        //create aliases
        NaturalNumber top = this.model.top();
        NaturalNumber bottom = this.model.bottom();

        //calculate power
        top.power(bottom.toInt());
        bottom.copyFrom(top);
        top.clear();

        updateViewToMatchModel(this.model, this.view);

    }

    @Override
    public void processRootEvent() {

        //create aliases
        NaturalNumber top = this.model.top();
        NaturalNumber bottom = this.model.bottom();

        //calculate root
        top.root(bottom.toInt());
        bottom.copyFrom(top);
        top.clear();

        updateViewToMatchModel(this.model, this.view);

    }

    @Override
    public void processAddNewDigitEvent(int digit) {

        //create aliases
        NaturalNumber bottom = this.model.bottom();

        //add digit
        bottom.multiplyBy10(digit);

        updateViewToMatchModel(this.model, this.view);

    }

}
