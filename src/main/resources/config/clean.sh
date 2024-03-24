kubectl delete -f redis.yaml -n re
kubectl delete -f mysql.yaml -n re
kubectl delete -f web-gateway.yaml -n re
kubectl delete -f webapp.yaml -n re
kubectl delete -f webserver.yaml -n re