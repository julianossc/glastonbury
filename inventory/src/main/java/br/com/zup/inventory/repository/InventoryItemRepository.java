package br.com.zup.inventory.repository;

import br.com.zup.inventory.entity.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventoryItemRepository extends JpaRepository<InventoryItem, String> {

    Optional<InventoryItem> findByName(String name);
}
