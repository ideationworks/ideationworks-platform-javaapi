include .make/Makefile.inc

VERSION			= $(shell git rev-parse HEAD)
APP     		?= ideationworks-platform-api
IMAGE			?= registry.gitlab.com/ideation.works/backend/$(APP):$(VERSION)
NS				?= default
PORT			?= 18080
DEBUG			?= false
MYSQL_HOST		?=
MYSQL_USER		?=
MYSQL_PASS		?=
MYSQL_DATABASE	?=
#ELASTICSEARCH_HOST	?=
#ELASTICSEARCH_PORT	?=
#ELASTICSEARCH_SCHEME	?=

.PHONY: all build test tag_latest release

all:	kubeme build push
deploy:	kubeme build push delete install

kubeme:

	kubectl config use-context md

jar:

	gradle bootJar

build:

	@echo "Building an image with the current tag $(IMAGE).."

	gradle bootJar

	docker build 	--rm 	\
					--tag $(IMAGE) .

	docker tag $(IMAGE) $(APP):latest

run: stop

	docker run 	--rm -d 				                        \
				--publish 8080:8080		                        \
				--name $(ALIAS)                                 \
				$(NAME):$(VERSION)

stop:

	docker rm -f $(ALIAS) | true

logs:

	docker logs -f $(ALIAS)

tag_latest:

	docker tag $(NAME):$(VERSION) $(NAME):latest

push:

	docker push $(IMAGE)
