from diagrams import Diagram, Cluster
from diagrams.custom import Custom
from diagrams.onprem.client import Users
from diagrams.programming.language import Python
from diagrams.onprem.ci import Jenkins
from diagrams.onprem.vcs import Github
from diagrams.onprem.analytics import ElasticSearch
from diagrams.onprem.container import Docker
from diagrams.generic.database import SQL
from diagrams.onprem.compute import Server

# Create a diagram
with Diagram("AI-Powered Self-Healing Test Automation Architecture", show=True, direction="LR"):

    user = Users("User")

    with Cluster("LangChain AI Layer"):
        langchain_nlp = Python("LangChain Chatbot (NLP)")
        self_healing = Python("Self-Healing Module (LangChain + Vector DB)")

    with Cluster("Test Execution"):
        test_engine = Python("Test Execution Engine")
        playwright = Python("Playwright/Cypress")

    with Cluster("Application Under Test"):
        orangehrm = Custom("OrangeHRM Web UI", "https://opensource-demo.orangehrmlive.com/favicon.ico")

    with Cluster("Logging & Monitoring"):
        test_logs = ElasticSearch("Test Logs & Analytics")

    with Cluster("CI/CD Pipeline"):
        ci_cd = Jenkins("Jenkins / GitHub Actions")

    # Connections
    user >> langchain_nlp >> test_engine >> playwright >> orangehrm
    playwright >> self_healing >> test_engine
    playwright >> test_logs >> ci_cd
