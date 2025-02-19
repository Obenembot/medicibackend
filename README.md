# Medici Service

### Some Checks that are made

1. Checks For fraudulent activities when updating a user. (By Id and Email)
2. Email Validation to avoid Duplicates
3. Audit History on User is kept (To See what happened on every user And Who made the changes)
4. Also password is Based64 encoded
5. Added a new api to update user password.
6. Email Validation added as well to check for proper email format. 

### Updates to be made

1. Extra Security is needed e.g Spring Security and keeping of Ip’s
2. Password should be encoded with BCryptPasswordEncoder

# Use Kubernetes to Build and Push to Docker Hub

### Build and Push Docker Image

```bash
docker build -t thembaembot/medicibeckend:latest .
docker login
docker push thembaembot/medicibeckend:latest
```

### Deploy to Kubernetes:

```bash
kubectl apply -f deployment.yaml
kubectl apply -f service.yaml
kubectl apply -f mysql-deployment.yaml
kubectl apply -f mysql-service.yaml
```

### Use to expose the application. expose on port 8081

```bash
kubectl port-forward service/medicibeckend 8081:80
```

###  Other Kube Commands

```bash
kubectl get pod
kubectl get service
kubectl delete service medicibeckend
kubectl delete pod medicibeckend
kubectl scale deployment medicibeckend --replicas=3
```