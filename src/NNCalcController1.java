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

        // update subtract button after we conditionally check it.
        if (model.bottom().compareTo(model.top()) <= 0) {
            view.updateSubtractAllowed(true);
        } else {
            view.updateSubtractAllowed(false);
        }

        // update the divide button after we conditionally check it.
        if (model.bottom().isZero()) {
            view.updateDivideAllowed(false);
        } else {
            view.updateDivideAllowed(true);
        }

        // update the power button after we conditionally check it.
        if (model.bottom().compareTo(INT_LIMIT) > 0) {
            view.updatePowerAllowed(false);
        } else {
            view.updatePowerAllowed(true);
        }

        // update the root button after we check it.
        if (model.bottom().compareTo(TWO) < 0
                || model.bottom().compareTo(INT_LIMIT) > 0) {
            view.updateRootAllowed(false);
        } else {
            view.updateRootAllowed(true);
        }

        // update the view so it changes it.
        view.updateTopDisplay(model.top());
        view.updateBottomDisplay(model.bottom());
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
         * Update model in response to this event
         */
        this.model.bottom().clear();
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
        /*
         * Update model in response to this event
         */
        this.model.top().newInstance().transferFrom(this.model.top());
        this.model.top().transferFrom(this.model.bottom());
        this.model.bottom().transferFrom(this.model.top().newInstance());
        /*
         * Update view to reflect changes in model
         */
        updateViewToMatchModel(this.model, this.view);
    }

    @Override
    public void processEnterEvent() {

        /*
         * Update model in response to this event
         */
        this.model.top().copyFrom(this.model.bottom());
        /*
         * Update view to reflect changes in model
         */
        updateViewToMatchModel(this.model, this.view);
    }

    @Override
    public void processAddEvent() {

        /*
         * Update model in response to this event
         */
        this.model.bottom().add(this.model.top());
        this.model.top().clear();
        /*
         * Update view to reflect changes in model
         */
        updateViewToMatchModel(this.model, this.view);
    }

    @Override
    public void processSubtractEvent() {
        /*
         * Update model in response to this event
         */
        this.model.top().subtract(this.model.bottom());
        this.model.bottom().transferFrom(this.model.top());

        updateViewToMatchModel(this.model, this.view);

    }

    @Override
    public void processMultiplyEvent() {

        /*
         * Update model in response to this event
         */
        this.model.top().multiply(this.model.bottom());
        this.model.bottom().transferFrom(this.model.top());
        /*
         * Update view to reflect changes in model
         */
        updateViewToMatchModel(this.model, this.view);
    }

    @Override
    public void processDivideEvent() {

        /*
         * Update model in response to this event
         */
        this.model.bottom().transferFrom(this.model.top());
        this.model.top()
                .transferFrom(this.model.top().divide(this.model.bottom()));
        /*
         * Update view to reflect changes in model
         */
        updateViewToMatchModel(this.model, this.view);
    }

    @Override
    public void processPowerEvent() {

        /*
         * Update model in response to this event
         */
        this.model.top().power(this.model.bottom().toInt());
        this.model.bottom().transferFrom(this.model.top());
        /*
         * Update view to reflect changes in model
         */
        updateViewToMatchModel(this.model, this.view);
    }

    @Override
    public void processRootEvent() {
        /*
         * Update model in response to this event
         */
        this.model.top().root(this.model.bottom().toInt());
        this.model.bottom().transferFrom(this.model.top());
        /*
         * Update view to reflect changes in model
         */
        updateViewToMatchModel(this.model, this.view);
    }

    @Override
    public void processAddNewDigitEvent(int digit) {
        // Get aliases to bottom from model

        /*
         * Update model in response to this event
         */
        this.model.bottom().multiplyBy10(digit);
        /*
         * Update view to reflect changes in model
         */
        updateViewToMatchModel(this.model, this.view);
    }
}
