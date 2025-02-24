const questions = [
  {
    question: "What is the capital of India?",
    options: ["New Delhi", "Mumbai", "Kolkata", "Chennai"],
    answer: "New Delhi",
  },
  {
    question: "Who is the first Prime Minister of India?",
    options: ["Jawaharlal Nehru", "Gandhi", "Indira Gandhi", "Rajiv Gandhi"],
    answer: "Jawaharlal Nehru",
  },
  {
    question: "Which river is considered the holiest in India?",
    options: ["Ganga", "Yamuna", "Brahmaputra", "Indus"],
    answer: "Ganga",
  },
  {
    question: "What is the national flower of India?",
    options: ["Rose", "Lotus", "Jasmine", "Tulip"],
    answer: "Lotus",
  },
  {
    question: "In which year did India gain independence?",
    options: ["1947", "1950", "1960", "1970"],
    answer: "1947",
  },
  {
    question: "Who is known as the Father of the Nation in India?",
    options: ["Mahatma Gandhi", "Jawaharlal Nehru", "Sardar Patel", "Bhagat Singh"],
    answer: "Mahatma Gandhi",
  },
  {
    question: "Which is the national animal of India?",
    options: ["Lion", "Elephant", "Tiger", "Peacock"],
    answer: "Tiger",
  },
  {
    question: "Which is the largest state in India by area?",
    options: ["Rajasthan", "Uttar Pradesh", "Maharashtra", "Madhya Pradesh"],
    answer: "Rajasthan",
  },
  {
    question: "Who wrote the Indian national anthem?",
    options: ["Rabindranath Tagore", "Mahatma Gandhi", "Bankim Chandra Chattopadhyay", "Subhas Chandra Bose"],
    answer: "Rabindranath Tagore",
  },
  {
    question: "Which is the longest river in India?",
    options: ["Ganga", "Yamuna", "Godavari", "Narmada"],
    answer: "Ganga",
  },
];

let currentPage = 0;
const questionsPerPage = 3;
let timeLeft = 60;
let userAnswers = new Array(questions.length).fill(null);

function startTimer() {
  setInterval(() => {
    if (timeLeft > 0) {
      timeLeft--;
      document.getElementById("time").textContent = timeLeft;
      document.getElementById("time-bar").style.width =
        (timeLeft / 60) * 100 + "%";
    }
  }, 1000);
}

function displayQuestions() {
  const container = document.getElementById("question-container");
  container.innerHTML = "";
  const start = currentPage * questionsPerPage;
  const end = start + questionsPerPage;
  const paginatedQuestions = questions.slice(start, end);
  paginatedQuestions.forEach((q, index) => {
    container.innerHTML += `
      <div class="mb-3 p-3 border rounded">
          <label class="form-label fw-bold">
            ${start + index + 1}. ${q.question}
          </label>
          ${q.options
            .map(
              (option) => `
            <div class="form-check">
                <input class="form-check-input" type="radio" name="q${
                  start + index
                }" value="${option}" ${
                userAnswers[start + index] === option ? "checked" : ""
              }>
                <label class="form-check-label">${option}</label>
            </div>
        `
            )
            .join("")}
      </div>
    `;
  });
  document.getElementById("prevBtn").disabled = currentPage === 0;
  document.getElementById("nextBtn").disabled =
    (currentPage + 1) * questionsPerPage >= questions.length;
  document.getElementById("submitBtn").style.display =
    currentPage * questionsPerPage >= questions.length ? "block" : "none";
}

document.getElementById("nextBtn").addEventListener("click", () => {
  document
    .querySelectorAll("input[type=radio]:checked")
    .forEach((input) => {
      let index = parseInt(input.name.substring(1));
      userAnswers[index] = input.value;
    });
  currentPage++;
  displayQuestions();
});

document.getElementById("prevBtn").addEventListener("click", () => {
  currentPage--;
  displayQuestions();
});

document.getElementById("quizForm").addEventListener("submit", (e) => {
  e.preventDefault();
  document
    .querySelectorAll("input[type=radio]:checked")
    .forEach((input) => {
      let index = parseInt(input.name.substring(1));
      userAnswers[index] = input.value;
    });
  let score = userAnswers.filter(
    (ans, idx) => ans === questions[idx].answer
  ).length;
  document.getElementById("result").innerHTML = `
    <h3>Your Score: ${score}/${questions.length}</h3>
  `;
});

displayQuestions();
startTimer();
