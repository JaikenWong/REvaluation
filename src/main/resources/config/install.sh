kubectl apply -f redis.yaml -n re
kubectl apply -f mysql.yaml -n re
kubectl apply -f web-gateway.yaml -n re
kubectl apply -f webapp.yaml -n re
kubectl apply -f webserver.yaml -n re