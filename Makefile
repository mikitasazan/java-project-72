setup:
	$(MAKE) -C app setup

start:
	$(MAKE) -C app start

test:
	$(MAKE) -C app test

.PHONY: setup start test
