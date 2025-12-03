# README
This project is a microservices-based Laboratory Management System. It includes service discovery, authentication, distributed tracing, and monitoring features among other features developed throughout the semester.


## Running the Application
Use the following commands to build and start the application locally using Docker:
```bash
mvn clean package -DskipTests
docker compose up --build -d
```

To cleanly shutdown the docker containers after start, use:
```bash
docker compose down
```

### Editing the Application
Use the following commands for rapidly stopping and starting the application locally using Docker:
``` bash
docker compose down
mvn clean package -DskipTests
docker compose up --build -d
```


## Servie Registry & Discovery
The system uses **Eureka** for service registry and discovery. Access the dashboard here:  
[Eureka Dashboard](http://localhost:8070/)


## Authentication & Authorization
This project uses **Keycloak** for authentication and **JWT** for authorization.
- **JWT Debugging:** [jwt.io](https://www.jwt.io/)

### Keycloak Admin Login
- **URL:** [Keycloak Admin](http://localhost:8080/)
- **Username:** `admin`
- **Password:** `admin`

### Keycloak Admin Login
- **URL:** [Keycloak User Account](http://localhost:8080/realms/realm-lab/account)

| User Role | Username | Password |
|:--- | :--- | :--- |
| ADMIN | lab-admin | password |
| USER | lab-user | password |


## Distributed Tracing
The system provides distributed tracing using **Zipkin** and log visualization with **Kibana**.
- **Zipkin:** [Zipkin Dashboard](http://localhost:9411/zipkin/)
- **Kibana:** [Kibana Dashboard](http://localhost:5601/)

### Configuring Kibana
1. Open a browser and go to [Kibana](http://localhost:5601/). You will see a Welcome page with two options:
    - Play with sample data
    - Explore your own data
2. Click **Explore on My Own**.
3. On the Add Data page, click the **Discover** icon on the left menu.
4. Create an **index pattern** so Kibana knows which Elasticsearch indexes to retrieve:
    - Click **Index Patterns** on the left menu.
    - Enter `logstash-*` as the index pattern and click **Next Step**.
5. Set up the **time filter**:
    - Choose `@timestamp` from the Time Filter Field Name dropdown.
    - Click **Create Index Pattern**.
6. Click **Discover** to view real-time service logs sent to ELK. You can filter, search, and analyze logs in real-time.

Kibana is now configured and ready to visualize your service logs.


## Monitoring Microservices
Prometheus is used to collect metrics, and Grafana is used for visualization.
- **Prometheus:** [Prometheus Dashboard](http://localhost:9090/targets)
- **Grafana:** [Grafana Dashboard](http://localhost:3000/login)

### Grafana Setup
1. **Login:**
    - Username: `admin`
    - Password: `password`
2. **Add Prometheus Data Source:**
    - Click **Data Sources** → **Add Your First Data Source**.
    - Select **Prometheus**.
    - Set the URL to `http://prometheus:9090`.
    - Click **Save & Test**.
3. **Import Dashboard:**
    - Click **Dashboard** → **Manage** → **Import**.
    - Enter the dashboard ID `11378` or URL `https://grafana.com/grafana/dashboards/11378/`.
    - Select **Prometheus** as the data source.
    - Click **Import** to view your dashboard with Micrometer metrics.

Your monitoring dashboards are now ready to visualize metrics from all microservices.