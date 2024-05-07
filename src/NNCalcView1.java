import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import components.naturalnumber.NaturalNumber;

/**
 * View class.
 *
 * @author Ashim Dhakal
 *
 */
public final class NNCalcView1 extends JFrame implements NNCalcView {

    /**
     * Controller object registered with this view to observe user-interaction
     * events.
     */
    private NNCalcController controller;

    /**
     * State of user interaction: last event "seen".
     */
    private enum State {
        /**
         * Last event was clear, enter, another operator, or digit entry, resp.
         */
        SAW_CLEAR, SAW_ENTER_OR_SWAP, SAW_OTHER_OP, SAW_DIGIT
    }

    /**
     * State variable to keep track of which event happened last; needed to
     * prepare for digit to be added to bottom operand.
     */
    private State currentState;

    /**
     * Text areas.
     */
    private final JTextArea tTop, tBottom;

    /**
     * Operator and related buttons.
     */
    private final JButton bClear, bSwap, bEnter, bAdd, bSubtract, bMultiply,
            bDivide, bPower, bRoot;

    /**
     * Digit entry buttons.
     */
    private final JButton[] bDigits;

    /**
     * Useful constants.
     */
    private static final int TEXT_AREA_HEIGHT = 5, TEXT_AREA_WIDTH = 20,
            DIGIT_BUTTONS = 10, MAIN_BUTTON_PANEL_GRID_ROWS = 4,
            MAIN_BUTTON_PANEL_GRID_COLUMNS = 4, SIDE_BUTTON_PANEL_GRID_ROWS = 3,
            SIDE_BUTTON_PANEL_GRID_COLUMNS = 1, CALC_GRID_ROWS = 3,
            CALC_GRID_COLUMNS = 1;

    /**
     * No argument constructor.
     */
    public NNCalcView1() {
        // Create the JFrame being extended
        /*
         * Call the JFrame (superclass) constructor with a String parameter to
         * name the window in its title bar
         */
        super("ashims natural number calculator");

        // Set up the GUI widgets --------------------------------------------

        /*
         * Set up initial state of GUI to behave like last event was "Clear";
         * currentState is not a GUI widget per se, but is needed to process
         * digit button events appropriately
         */

        // Create widgets

        this.currentState = State.SAW_CLEAR;

        this.bDigits = new JButton[DIGIT_BUTTONS];
        this.tTop = new JTextArea("", TEXT_AREA_HEIGHT, TEXT_AREA_WIDTH);
        this.tBottom = new JTextArea("", TEXT_AREA_HEIGHT, TEXT_AREA_WIDTH);
        this.bDivide = new JButton("%");
        this.bAdd = new JButton("+");
        this.bMultiply = new JButton("*");
        this.bClear = new JButton("clr");
        this.bEnter = new JButton("enter");
        this.bRoot = new JButton("root");
        this.bSwap = new JButton("swap");
        this.bPower = new JButton("^");
        this.bSubtract = new JButton("-");
        int iterate = 0;
        while (iterate < DIGIT_BUTTONS) {
            this.bDigits[iterate] = new JButton(String.valueOf(iterate));
            iterate++;
        }

        // Set up the GUI widgets --------------------------------------------

        /*
         * Text areas should wrap lines, and should be read-only; they cannot be
         * edited because allowing keyboard entry would require checking whether
         * entries are digits, which we don't want to have to do
         */

        this.tTop.setEditable(false);
        this.tTop.setLineWrap(true);
        this.tTop.setWrapStyleWord(true);
        this.tBottom.setEditable(false);
        this.tBottom.setLineWrap(true);
        this.tBottom.setWrapStyleWord(true);

        /*
         * Initially, the following buttons should be disabled: divide (divisor
         * must not be 0) and root (root must be at least 2) -- hint: see the
         * JButton method setEnabled
         */
        this.bRoot.setEnabled(false);
        this.bDivide.setEnabled(false);

        /*
         * Create scroll panes for the text areas in case number is long enough
         * to require scrolling
         */

        JScrollPane tTopScrollPane = new JScrollPane(this.tTop);
        JScrollPane tBottomScrollPane = new JScrollPane(this.tBottom);

        //Create main button panel

        JPanel panelButtons = new JPanel(new GridLayout(
                MAIN_BUTTON_PANEL_GRID_ROWS, MAIN_BUTTON_PANEL_GRID_COLUMNS));

        /*
         * Add the buttons to the main button panel, from left to right and top
         * to bottom
         */

        // declaring numbers so we have no magic number warnings.
        final int zero = 0;
        final int one = 1;
        final int two = 2;
        final int three = 3;
        final int four = 4;
        final int five = 5;
        final int six = 6;
        final int seven = 7;
        final int eight = 8;
        final int nine = 9;

        // row 1
        panelButtons.add(this.bDigits[zero]);
        panelButtons.add(this.bDigits[one]);
        panelButtons.add(this.bDigits[two]);
        panelButtons.add(this.bAdd);

        // row 2
        panelButtons.add(this.bDigits[three]);
        panelButtons.add(this.bDigits[four]);
        panelButtons.add(this.bDigits[five]);
        panelButtons.add(this.bSubtract);

        // row 3
        panelButtons.add(this.bDigits[six]);
        panelButtons.add(this.bDigits[seven]);
        panelButtons.add(this.bDigits[eight]);
        panelButtons.add(this.bMultiply);

        // row 4
        panelButtons.add(this.bDigits[nine]);
        panelButtons.add(this.bPower);
        panelButtons.add(this.bRoot);
        panelButtons.add(this.bDivide);

        // side buttons

        JPanel sideButons = new JPanel(new GridLayout(
                SIDE_BUTTON_PANEL_GRID_ROWS, SIDE_BUTTON_PANEL_GRID_COLUMNS));

        /*
         * Add the buttons to the side button panel, from left to right and top
         * to bottom
         */
        // swap button
        sideButons.add(this.bSwap);
        // clear button
        sideButons.add(this.bClear);
        // enter button
        sideButons.add(this.bEnter);

        /*
         * Create combined button panel organized using flow layout, which is
         * simple and does the right thing: sizes of nested panels are natural,
         * not necessarily equal as with grid layout
         */

        JPanel allPanels = new JPanel(new FlowLayout());

        // Add the other two button panels to the combined button panel

        allPanels.add(panelButtons);
        allPanels.add(sideButons);

        //  main window

        this.setLayout(new GridLayout(CALC_GRID_ROWS, CALC_GRID_COLUMNS));

        /*
         * Add scroll panes and button panel to main window, from left to right
         * and top to bottom
         */

        this.add(tTopScrollPane);
        this.add(tBottomScrollPane);
        this.add(allPanels);

        // set up the observers ----------------------------------------------

        this.bClear.addActionListener(this);
        this.bDivide.addActionListener(this);
        this.bPower.addActionListener(this);
        this.bMultiply.addActionListener(this);
        this.bRoot.addActionListener(this);
        this.bSubtract.addActionListener(this);
        this.bAdd.addActionListener(this);
        this.bEnter.addActionListener(this);
        this.bSwap.addActionListener(this);

        int iterating = 0;
        while (iterating < DIGIT_BUTTONS) {
            this.bDigits[iterating].addActionListener(this);
            iterating++;
        }

        // Set up the main application window --------------------------------

        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    @Override
    public void registerObserver(NNCalcController controller) {

        this.controller = controller;
        // controller being set
    }

    @Override
    public void updateTopDisplay(NaturalNumber n) {

        // top display being set
        this.tTop.setText(n.toString());

    }

    @Override
    public void updateBottomDisplay(NaturalNumber n) {

        // bottom display being set
        this.tBottom.setText(n.toString());
    }

    @Override
    public void updateSubtractAllowed(boolean allowed) {

        // subtracting allowed
        this.bSubtract.setEnabled(allowed);

    }

    @Override
    public void updateDivideAllowed(boolean allowed) {

        // divide allowed
        this.bDivide.setEnabled(allowed);

    }

    @Override
    public void updatePowerAllowed(boolean allowed) {

        // power allowed
        this.bPower.setEnabled(allowed);

    }

    @Override
    public void updateRootAllowed(boolean allowed) {

        // root allowed
        this.bRoot.setEnabled(allowed);

    }

    @Override
    public void actionPerformed(ActionEvent event) {
        /*
         * Set cursor to indicate computation on-going; this matters only if
         * processing the event might take a noticeable amount of time as seen
         * by the user
         */
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        /*
         * Determine which event has occurred that we are being notified of by
         * this callback; in this case, the source of the event (i.e, the widget
         * calling actionPerformed) is all we need because only buttons are
         * involved here, so the event must be a button press; in each case,
         * tell the controller to do whatever is needed to update the model and
         * to refresh the view
         */

        /*
         * just goes through n checks if it is clear, can swap, enter, add,
         * subtract, multiply, divide, power, root
         */
        if (event.getSource() != this.bClear && event.getSource() != this.bSwap
                && event.getSource() != this.bEnter
                && event.getSource() != this.bAdd
                && event.getSource() != this.bSubtract
                && event.getSource() != this.bMultiply
                && event.getSource() != this.bDivide
                && event.getSource() != this.bPower
                && event.getSource() != this.bRoot) {
            // for when it can, it goes through and runs it.
            for (int i = 0; i < DIGIT_BUTTONS; i++) {
                if (event.getSource() == this.bDigits[i]) {
                    boolean processingTheDigits = true;
                    if (this.currentState == State.SAW_ENTER_OR_SWAP) {
                        this.controller.processClearEvent();
                        this.currentState = State.SAW_DIGIT;
                        processingTheDigits = true;
                    } else if (this.currentState == State.SAW_OTHER_OP) {
                        this.controller.processEnterEvent();
                        this.controller.processClearEvent();
                        this.currentState = State.SAW_DIGIT;
                        processingTheDigits = true;
                    }
                    if (processingTheDigits) {
                        this.controller.processAddNewDigitEvent(i);
                        // processing
                    }
                    this.currentState = State.SAW_DIGIT;
                }
            }
        }
        // goes through and performs actions based on the button clicked
        if (event.getSource() == this.bClear) {
            this.controller.processClearEvent();
            this.currentState = State.SAW_CLEAR;
        } else if (event.getSource() == this.bMultiply) {
            this.controller.processMultiplyEvent();
            this.currentState = State.SAW_OTHER_OP;
        } else if (event.getSource() == this.bEnter) {
            this.controller.processEnterEvent();
            this.currentState = State.SAW_ENTER_OR_SWAP;
        } else if (event.getSource() == this.bDivide) {
            this.controller.processDivideEvent();
            this.currentState = State.SAW_OTHER_OP;
        } else if (event.getSource() == this.bRoot) {
            this.controller.processRootEvent();
            this.currentState = State.SAW_OTHER_OP;
        } else if (event.getSource() == this.bPower) {
            this.controller.processPowerEvent();
            this.currentState = State.SAW_OTHER_OP;
        } else if (event.getSource() == this.bAdd) {
            this.controller.processAddEvent();
            this.currentState = State.SAW_OTHER_OP;
        } else if (event.getSource() == this.bSwap) {
            this.controller.processSwapEvent();
            this.currentState = State.SAW_ENTER_OR_SWAP;
        } else if (event.getSource() == this.bSubtract) {
            this.controller.processSubtractEvent();
            this.currentState = State.SAW_OTHER_OP;
        }

        // cursor back to normal

        this.setCursor(Cursor.getDefaultCursor());
    }
}
