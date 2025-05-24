// Select calculator container
function createCalculator() {
    // Add a title to the calculator
    const title = document.createElement("h2");
    title.innerText = "Simple Calculator";
    title.className = "text-center mb-3";
    calculator.appendChild(title);

    // Add the display input field
    const display = document.createElement("input");
    display.type = "text";
    display.id = "display";
    display.className = "form-control mb-3";
    display.placeholder = "0";
    display.disabled = true;
    calculator.appendChild(display);

    // Create a container for buttons
    const buttonsContainer = document.createElement("div");
    buttonsContainer.className = "row g-2";

    const buttons = [
        "7", "8", "9", "/",
        "4", "5", "6", "*",
        "1", "2", "3", "-",
        "0", ".", "=", "+",
        "C", "M+", "M-", "MC"
    ];

    buttons.forEach((btn, index) => {
        const button = document.createElement("button");
        button.innerText = btn;
        button.className = "btn btn-secondary col-3";
        button.onclick = () => handleButtonClick(btn);
        buttonsContainer.appendChild(button);
    });

    calculator.appendChild(buttonsContainer);
}


// Initialize calculator state
let memory = 0;
let currentExpression = "";

// Handle button clicks
function handleButtonClick(buttonValue) {
    const display = document.getElementById("display");

    if (!isNaN(buttonValue) || buttonValue === ".") {
        currentExpression += buttonValue;
        display.value = currentExpression;
    } else if (["+", "-", "*", "/"].includes(buttonValue)) {
        currentExpression += ` ${buttonValue} `;
        display.value = currentExpression;
    } else if (buttonValue === "=") {
        try {
            const result = eval(currentExpression);
            display.value = result;
            currentExpression = result.toString();
        } catch {
            display.value = "Error";
            currentExpression = "";
        }
    } else if (buttonValue === "C") {
        currentExpression = "";
        display.value = "0";
    } else if (buttonValue === "M+") {
        memory += parseFloat(display.value) || 0;
        alert(`Memory: ${memory}`);
    } else if (buttonValue === "M-") {
        memory -= parseFloat(display.value) || 0;
        alert(`Memory: ${memory}`);
    } else if (buttonValue === "MC") {
        memory = 0;
        alert("Memory Cleared");
    }
}

// Only allow number inputs via keyboard
document.addEventListener("keydown", (event) => {
    const key = event.key;
    const allowedKeys = "0123456789.+-*/=C";

    if (!allowedKeys.includes(key) && key !== "Enter" && key !== "Backspace") {
        event.preventDefault();
        alert("Only numbers are allowed");
    } else if (key === "Enter") {
        handleButtonClick("=");
    }
});

// Run the calculator setup
createCalculator();
