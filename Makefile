setup:
	$(MAKE) -C app setup

test:
	$(MAKE) -C app test

.PHONY: setup test
