package be.vizit.vim.services;

import be.vizit.vim.domain.entities.InventoryItem;
import be.vizit.vim.repository.InventoryitemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InventoryItemService {

  private final InventoryitemRepository inventoryitemRepository;
  private final QrService qrService;

  @Autowired
  public InventoryItemService(InventoryitemRepository inventoryitemRepository,
      QrService qrService) {
    this.inventoryitemRepository = inventoryitemRepository;
    this.qrService = qrService;
  }

  @Transactional(readOnly = true)
  public InventoryItem findByUuid(String uuid) {
    return inventoryitemRepository.findByUuid(uuid);
  }

  @Transactional(readOnly = true)
  public InventoryItem getInventoryItem(long id) {
    return inventoryitemRepository.findInventoryItemById(id);
  }

  @Transactional
  public void delete(InventoryItem inventoryitem) {
    inventoryitem.setActive(false);
  }

  @Transactional
  public void save(InventoryItem inventoryItem) {
    inventoryitemRepository.save(inventoryItem);
  }

  public byte[] getQrCode(InventoryItem inventoryitem) {
    return qrService.generateQr(inventoryitem.getUuid(), 100, 100);
  }
}
