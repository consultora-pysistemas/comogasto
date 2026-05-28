include .env

up:
	docker compose up -d

down:
	docker compose down

logs:
	docker compose logs -f

ps:
	docker compose ps

restart:
	docker compose down && docker compose up -d

build:
	docker compose build

clean:
	docker compose down -v

infra-up:
	docker compose -f docker-compose.yml up -d

platform-up:
	docker compose -f docker-compose.yml -f docker-compose.platform.yml up -d

observability-up:
	docker compose -f docker-compose.observability.yml up -d

topics:
	sh scripts/create-topics.sh

health:
	sh scripts/healthcheck.sh

pmm-up:
	docker compose up -d pmm-server

pmm-logs:
	docker compose logs pmm-server -f

pmm-status:
	docker compose ps pmm-server

pmm-down:
	docker compose down -v pmm-server

pmm-url:
	@echo "Percona PMM available at: http://localhost:${PMM_SERVER_PORT}"
	@echo "Credentials: admin / admin"
