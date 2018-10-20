package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class OwnerMapServiceTest {
    OwnerService ownerService;
    final Long ownerId = 1L;
    final String lastName = "Doe";

    @BeforeEach
    void setUp() {
        ownerService = new OwnerMapService(new PetTypeMapService(), new PetMapService());
        ownerService.save(Owner.builder().id(ownerId).lastName(lastName).build());
    }

    @Test
    void findAll() {
        Set<Owner> ownerSet = ownerService.findAll();
        assertEquals(1, ownerSet.size());
    }

    @Test
    void findById() {
        assertEquals(ownerId, ownerService.findById(ownerId).getId());
    }

    @Test
    void findByLastName() {
        Owner owner = ownerService.findByLastName(this.lastName);
        assertNotNull(owner);
        assertEquals(ownerId, owner.getId());
        assertEquals(lastName, owner.getLastName());

    }

    @Test
    void findByLastNameNotFound() {
        Owner owner = ownerService.findByLastName("foo");
        assertNull(owner);
    }

    @Test
    void saveExistingId() {
        Long ownerId2 = 2L;
        ownerService.save(Owner.builder().id(ownerId2).build());
        assertEquals(ownerId2, ownerService.findById(ownerId2).getId());
    }

    @Test
    void saveNoId() {
        Owner owner = ownerService.save(Owner.builder().build());
        assertNotNull(owner);
        assertNotNull(owner.getId());
    }

    @Test
    void delete() {
        ownerService.delete(ownerService.findById(ownerId));

        assertEquals(0, ownerService.findAll().size());
    }

    @Test
    void deleteById() {
        ownerService.deleteById(ownerId);

        assertEquals(0, ownerService.findAll().size());
    }
}
